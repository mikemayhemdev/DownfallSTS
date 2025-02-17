package gremlin.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.GainPennyEffect;
import gremlin.GremlinMod;
import gremlin.actions.ShackleAction;
import gremlin.actions.StealArtifactAction;

import static gremlin.GremlinMod.MAD_GREMLIN;

public class Pickpocket extends AbstractGremlinCard {
    public static final String ID = getID("Pickpocket");
    private static final CardStrings strings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = strings.NAME;
    private static final String IMG_PATH = "cards/pickpocket.png";

    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;
    private static final AbstractCard.CardRarity RARITY = CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ENEMY;

    private static final int COST = 2;
    private static final int POWER = 9;
    private static final int MAGIC = 2;
    private static final int UPGRADE_BONUS = 4;

    public Pickpocket()
    {
        super(ID, NAME, IMG_PATH, COST, strings.DESCRIPTION, TYPE, RARITY, TARGET);
        this.baseMagicNumber = MAGIC;
        this.magicNumber = baseMagicNumber;
        this.baseDamage = POWER;
        this.baseBlamage = 5;
        this.exhaust = true;
        this.tags.add(MAD_GREMLIN);
        setBackgrounds();
        GremlinMod.loadJokeCardImage(this, "Pickpocket.png");
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage,
                this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));

        AbstractDungeon.actionManager.addToBottom(new ShackleAction(m, magicNumber));
        AbstractDungeon.actionManager.addToBottom(new ShackleAction(m, magicNumber));

        if (!this.upgraded) {
            {
                AbstractDungeon.player.gainGold(5);
                for (int i = 0; i < 5; ++i) {
                    AbstractDungeon.effectList.add(new GainPennyEffect(p, p.hb.cX, p.hb.cY, p.hb.cX, p.hb.cY, true));
                }
            }
        }

        if (this.upgraded) {
            {
                AbstractDungeon.player.gainGold(10);
                for (int i = 0; i < 10; ++i) {
                    AbstractDungeon.effectList.add(new GainPennyEffect(p, p.hb.cX, p.hb.cY, p.hb.cX, p.hb.cY, true));
                }
            }
        }

        }

    @Override
    public void upgrade() {
        if (!this.upgraded)
        {
            upgradeName();
            upgradeBlammage(5);
            upgradeDamage(UPGRADE_BONUS);
            upgradeMagicNumber(1);
        }
    }
}
