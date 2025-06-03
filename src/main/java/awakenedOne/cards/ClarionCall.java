package awakenedOne.cards;

import awakenedOne.AwakenedOneMod;
import awakenedOne.relics.KTRibbon;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.cards.tempCards.Insight;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import hermit.relics.BartenderGlass;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static awakenedOne.ui.AwakenButton.awaken;
import static awakenedOne.util.Wiz.*;

public class ClarionCall extends AbstractAwakenedCard {

    public final static String ID = AwakenedOneMod.makeID(ClarionCall.class.getSimpleName());
    // intellij stuff skill, self, basic, , ,  5, 3, ,

    public ClarionCall() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseBlock = 5;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        if (checkVoid()) {
            this.addToTop(new DrawCardAction(AbstractDungeon.player, 1));
            this.addToTop(new GainEnergyAction(1));
            atb(new AbstractGameAction() {
                @Override
                public void update() {
                    isDone = true;
                    ArrayList<AbstractCard> valid = new ArrayList<>();
                    valid.addAll(AbstractDungeon.player.hand.group.stream().filter(q -> q instanceof VoidCard).collect(Collectors.toList()));
                    if (!valid.isEmpty()) {
                        att(new ExhaustSpecificCardAction(valid.get(AbstractDungeon.cardRandomRng.random(valid.size()-1)), AbstractDungeon.player.hand));
                    }
                }
            });
        }
    }


    public static boolean checkVoid() {
        boolean hasVoid = false;
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c instanceof VoidCard) {
                hasVoid = true;
            }
        }
        return hasVoid;
    }

    @Override
    public void triggerOnGlowCheck() {
        if (checkVoid()) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        } else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        }
    }

    @Override
    public void upp() {
        upgradeDamage(3);
    }
}