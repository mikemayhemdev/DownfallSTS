package champ.cards;

import champ.ChampMod;
import champ.actions.ModifyDamageAndMagicAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static champ.ChampMod.fatigue;

public class EnragedBash extends AbstractChampCard {

    public final static String ID = makeID("EnragedBash");

    //stupid intellij stuff attack, enemy, uncommon

    private static final int DAMAGE = 5;
    private static final int MAGIC = 1;

    public EnragedBash() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;
       // baseCool = cool = 2;
        myHpLossCost = 5;
        tags.add(ChampMod.COMBO);
        tags.add(ChampMod.COMBOBERSERKER);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

        int chosen = 0;
        for (int i = 0; i < magicNumber; i++) {
            chosen = AbstractDungeon.cardRng.random(0,2);
            switch(chosen) {
                case 0: {
                    dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
                    break;
                }
                case 1: {
                    dmg(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
                    break;
                }
                case 2: {
                    dmg(m, AbstractGameAction.AttackEffect.SMASH);
                    break;
                }
            }
        }
        if (bcombo())
            atb(new ModifyDamageAndMagicAction(uuid, 1));

        fatigue(5);
    }

    @Override
    public void triggerOnGlowCheck() {
        glowColor = bcombo() ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }

    public void upp() {
        upgradeMagicNumber(1);
        this.rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}