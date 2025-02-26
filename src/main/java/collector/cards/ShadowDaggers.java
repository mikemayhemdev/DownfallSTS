package collector.cards;

import collector.cards.collectibles.AbstractCollectibleCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;

import static collector.CollectorMod.makeID;

public class ShadowDaggers extends AbstractCollectorCard {
    public final static String ID = makeID(ShadowDaggers.class.getSimpleName());
    // intellij stuff attack, enemy, uncommon, 4, 2, , , ,

    public ShadowDaggers() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 3;
        exhaust = true;
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < AbstractDungeon.actionManager.cardsPlayedThisCombat.stream().filter(q -> q instanceof AbstractCollectibleCard).count(); i++) {
            dmg(m, AbstractGameAction.AttackEffect.SLASH_DIAGONAL);
        }
    }

    public void applyPowers() {
        super.applyPowers();
        int count = (int) AbstractDungeon.actionManager.cardsPlayedThisCombat.stream().filter(q -> q instanceof AbstractCollectibleCard).count();

        this.rawDescription = cardStrings.DESCRIPTION;
        this.rawDescription = this.rawDescription + cardStrings.EXTENDED_DESCRIPTION[0] + count;
        this.rawDescription = count == 1 ? this.rawDescription + cardStrings.EXTENDED_DESCRIPTION[1] : this.rawDescription + cardStrings.EXTENDED_DESCRIPTION[2];

        this.initializeDescription();
    }

    public void onMoveToDiscard() {
        this.rawDescription = cardStrings.DESCRIPTION;
        this.initializeDescription();
    }

    public void upp() {
        upgradeDamage(2);
    }
}