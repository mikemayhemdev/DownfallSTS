package champ.cards;

import champ.ChampMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Duel extends AbstractChampCard {

    public final static String ID = makeID("Duel");

    //stupid intellij stuff attack, enemy, uncommon

    private static final int DAMAGE = 7;
    private static final int UPG_DAMAGE = 3;

    private static final int BLOCK = 7;
    private static final int UPG_BLOCK = 3;

    public Duel() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        baseBlock = BLOCK;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (upgraded) techique();
        blck();
        dmg(m, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
        if (monsterList().size() == 1 && !this.purgeOnUse) {
            AbstractCard r = this;
            atb(new AbstractGameAction() {
                @Override
                public void update() {
                    isDone = true;
                    GameActionManager.queueExtraCard(r, m);
                }
            });
        }
    }

    @Override
    public void triggerOnGlowCheck() {
        glowColor = (monsterList().size() == 1) ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }

    public void upp() {
        tags.add(ChampMod.TECHNIQUE);
        upgradeDamage(1);
        upgradeBlock(1);
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}