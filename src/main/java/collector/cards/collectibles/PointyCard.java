package collector.cards.collectibles;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;

import static collector.CollectorMod.banditBoost;
import static collector.CollectorMod.makeID;
import static collector.util.Wiz.atb;

public class PointyCard extends AbstractCollectibleCard {
    public final static String ID = makeID(PointyCard.class.getSimpleName());
    // intellij stuff attack, enemy, common, 5, 2, , , , 

    public PointyCard() {
        super(ID, 1, CardType.ATTACK, CardRarity.SPECIAL, CardTarget.ENEMY);
        baseDamage = 4;
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
        atb(new MakeTempCardInHandAction(new Shiv()));
        atb(new MakeTempCardInHandAction(new Shiv()));
        if (banditBoost(2))
            atb(new MakeTempCardInHandAction(new Shiv()));

    }

    public void upp() {
        upgradeDamage(4);
    }


    @Override
    public void triggerOnGlowCheck() {
        if (banditBoost(2)) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR;
            return;
        }
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR;
    }
}