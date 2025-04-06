package champ.cards;

import champ.ChampMod;
import champ.stances.AbstractChampStance;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static champ.ChampMod.loadJokeCardImage;

public class RecklessLeap extends AbstractChampCard {

    public final static String ID = makeID("RecklessLeap");

    //stupid intellij stuff attack, self_and_enemy, uncommon

    private static final int DAMAGE = 6;
    private static final int UPG_DAMAGE = 3;

    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 1;

    public RecklessLeap() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;
        //myHpLossCost = MAGIC;
        tags.add(ChampMod.OPENER);
        this.tags.add(ChampMod.OPENERBERSERKER);
        exhaust = true;
        tags.add(CardTags.STRIKE);
        loadJokeCardImage(this, "RecklessLeap.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        berserkOpen();
        dmg(m, AbstractGameAction.AttackEffect.SLASH_HEAVY);
        applyToSelf(new StrengthPower(p, magicNumber));
    }

    public void upp() {
        exhaust = false;
        upgradeDamage(3);
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}