package champ.cards;

import champ.ChampMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class RisingSlash extends AbstractChampCard {

    public final static String ID = makeID("RisingSlash");

    //stupid intellij stuff attack, enemy, uncommon

    private static final int DAMAGE = 7;
    private static final int UPG_DAMAGE = 3;

    public RisingSlash() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        tags.add(ChampMod.TECHNIQUE);
        tags.add(CardTags.STRIKE);
        techniqueLast = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        techique();
        dmg(m, AbstractGameAction.AttackEffect.SLASH_DIAGONAL);
        if (AbstractDungeon.actionManager.cardsPlayedThisCombat.size() > 1)
            if (AbstractDungeon.actionManager.cardsPlayedThisCombat.get(AbstractDungeon.actionManager.cardsPlayedThisCombat.size() - 2).hasTag(ChampMod.TECHNIQUE) && !this.purgeOnUse) {
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
        if (!AbstractDungeon.actionManager.cardsPlayedThisCombat.isEmpty())
            glowColor = (AbstractDungeon.actionManager.cardsPlayedThisCombat.get(AbstractDungeon.actionManager.cardsPlayedThisCombat.size() - 1).hasTag(ChampMod.TECHNIQUE) && !this.purgeOnUse ? AbstractCard.GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR);
    }

    public void upp() {
        upgradeDamage(UPG_DAMAGE);
    }
}