package automaton.cardmods;

import automaton.cards.FunctionCard;
import automaton.powers.FullReleasePower;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class FullReleaseCardMod extends BronzeCardMod {

    public static String ID = "bronze:FullReleaseModifier";

    private AbstractCard.CardType originalType;
    private AbstractCard.CardTarget originalTarget;

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return CardCrawlGame.languagePack.getPowerStrings("bronze:FullRelease").DESCRIPTIONS[0] + rawDescription;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        originalType = card.type;
        originalTarget = card.target;
        card.type = AbstractCard.CardType.POWER;
        ((FunctionCard) card).setBackgroundTexture("bronzeResources/images/512/bg_power_function.png", "bronzeResources/images/1024/bg_power_function.png");
        card.target = AbstractCard.CardTarget.SELF;
        for (AbstractCardModifier c : CardModifierManager.getModifiers(card, CardEffectsCardMod.ID)) {
            if (c instanceof CardEffectsCardMod) {
                ((CardEffectsCardMod) c).doUseEffects = false;
            }
        }
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        atb(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                AbstractCard q = card.makeStatEquivalentCopy();
                for (AbstractCardModifier c : CardModifierManager.getModifiers(q, CardEffectsCardMod.ID)) {
                    if (c instanceof CardEffectsCardMod) {
                        ((CardEffectsCardMod) c).doUseEffects = true;
                    }
                }
                q.type = originalType;
                if (q.type == AbstractCard.CardType.SKILL) {
                    ((FunctionCard) q).setBackgroundTexture("bronzeResources/images/512/bg_skill_function.png", "bronzeResources/images/1024/bg_skill_function.png");
                } else {
                    ((FunctionCard) q).setBackgroundTexture("bronzeResources/images/512/bg_attack_function.png", "bronzeResources/images/1024/bg_attack_function.png");
                }
                q.target = originalTarget;
                att(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new FullReleasePower(q)));
            }
        });
    }

    @Override
    public boolean removeOnCardPlayed(AbstractCard card) {
        return true;
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new FullReleaseCardMod();
    }
}
