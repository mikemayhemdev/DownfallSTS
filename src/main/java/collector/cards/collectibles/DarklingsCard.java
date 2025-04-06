package collector.cards.collectibles;

import basemod.helpers.CardModifierManager;
import collector.cardmods.CollectedCardMod;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.atb;

public class DarklingsCard extends AbstractCollectibleCard {
    public final static String ID = makeID(DarklingsCard.class.getSimpleName());
    // intellij stuff skill, self, common, , , , , , 

    public DarklingsCard() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        isPyre();
        baseDamage = 8;
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        atb(new SelectCardsAction(AbstractDungeon.player.exhaustPile.group, cardStrings.EXTENDED_DESCRIPTION[0], (card) -> CardModifierManager.hasModifier(card, CollectedCardMod.ID), (cards) -> {
            for (AbstractCard c : cards) {
                c.unfadeOut();
                AbstractDungeon.player.hand.addToHand(c);
                AbstractDungeon.player.exhaustPile.removeCard(c);
                c.unhover();
                c.fadingOut = false;
            }
        }));
    }

    public void upp() {
        upgradeDamage(4);
    }
}