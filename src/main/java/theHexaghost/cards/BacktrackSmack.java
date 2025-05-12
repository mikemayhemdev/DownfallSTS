package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;
import theHexaghost.HexaMod;
import theHexaghost.actions.RetractAction;

public class BacktrackSmack extends AbstractHexaCard {
    public final static String ID = makeID("BacktrackSmack");

    public BacktrackSmack() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 6;
        tags.add(HexaMod.GHOSTWHEELCARD);
        this.tags.add(SneckoMod.BANNEDFORSNECKO);

        HexaMod.loadJokeCardImage(this, "BacktrackSmack.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, makeInfo(), AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        dmg(m, makeInfo(), AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        atb(new RetractAction());
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
//            rawDescription = UPGRADE_DESCRIPTION;
//            initializeDescription();
            upgradeDamage(2);
        }
    }
}